
from fastapi import FastAPI, UploadFile, File, HTTPException
from PIL import Image, UnidentifiedImageError
from torchvision import transforms
from model import TamperingCNN

import torch
import io

app = FastAPI(title="Image Tampering Detection API")

# Device configuration
device = torch.device(
    "cuda" if torch.cuda.is_available() else "cpu"
)

# Load trained model
model = TamperingCNN().to(device)

model.load_state_dict(
    torch.load(
        "batchnorm_tampering_cnn.pth",
        map_location=device,
        weights_only=True
    )
)

model.eval()

# Image preprocessing
transform = transforms.Compose([

    transforms.Resize((224, 224)),

    transforms.ToTensor(),

    transforms.Normalize(
        mean=[0.5, 0.5, 0.5],
        std=[0.5, 0.5, 0.5]
    )

])

@app.get("/")
def home():

    return {
        "message": "Image Tampering Detection API is running"
    }


@app.post("/predict")
async def predict(file: UploadFile = File(...)):

    try:

        image_data = await file.read()

        image = Image.open(
            io.BytesIO(image_data)
        ).convert("RGB")

        image_tensor = transform(image).unsqueeze(0).to(device)

        with torch.no_grad():

            outputs = model(image_tensor)

            probabilities = torch.softmax(outputs, dim=1)[0]

        real_score = probabilities[0].item() * 100

        tampered_score = probabilities[1].item() * 100

        prediction = (
            "TAMPERED"
            if probabilities[1] >= probabilities[0]
            else "REAL"
        )

        return {

            "filename": file.filename,

            "prediction": prediction,

            "real_score": round(real_score, 2),

            "tampered_score": round(tampered_score, 2)

        }

    except (UnidentifiedImageError, OSError, ValueError):

        raise HTTPException(
            status_code=400,
            detail="Invalid or unsupported image file"
        )

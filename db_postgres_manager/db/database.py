from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from dotenv import load_dotenv
import os

# Load environment variables from .env file.
load_dotenv()

# Create database engine using the DB_URL from .env.
engine = create_engine(os.getenv("DB_URL"), echo=True)

# Create a session factory bound to the engine.
Session = sessionmaker(bind=engine)
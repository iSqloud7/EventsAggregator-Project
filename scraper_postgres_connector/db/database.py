from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from dotenv import load_dotenv
import os

# load .env
load_dotenv()
DB_URL = os.getenv("DB_URL")

# engine и session
engine = create_engine(DB_URL, echo=True)
Session = sessionmaker(bind=engine)
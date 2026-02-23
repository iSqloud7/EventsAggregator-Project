from backend.db.models import Base
from backend.db.database import engine

Base.metadata.create_all(engine)

print("Database tables created successfully!")
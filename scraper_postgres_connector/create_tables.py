from scraper_postgres_connector.db.models import Base
from scraper_postgres_connector.db.database import engine

Base.metadata.create_all(engine)

print("Database tables created successfully!")
import json
import sys
import os
from pathlib import Path

# Add the scraper_postgres_connector directory to the Python path.
# This allows relative imports like "from db.models import ...".
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))

from db.models import Base
from db.database import engine
from services.event_service import load_events_from_json


def create_tables():
    # Create all tables defined in models.py if they don't exist.
    Base.metadata.create_all(engine)
    print("Tables created successfully!")


def populate():
    # Load scraped events from the JSON output file.
    file_path = Path("mktickets_scraper/events-output/events.json")
    with open(file_path, encoding="utf-8") as f:
        events = json.load(f)

    # Sync events into the database.
    load_events_from_json(events)


if __name__ == "__main__":

    # Step 1: Create tables.
    create_tables()

    # Step 2: Populate with scraped data.
    populate()

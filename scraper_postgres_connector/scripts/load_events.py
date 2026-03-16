import json
from pathlib import Path
from scraper_postgres_connector.services.event_service import load_events_from_json

file_path = Path("mktickets_scraper/events-output/events.json")

with open(file_path, encoding="utf-8") as f:
    events = json.load(f)

load_events_from_json(events)

print(f"Loaded {len(events)} events")
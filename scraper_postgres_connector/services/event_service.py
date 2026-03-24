from db.database import Session
from db.models import Event


def load_events_from_json(events: list):
    # Open a new database session.
    session = Session()

    try:

        # Build a set of keys (title, date) from the scraped events.
        new_event_keys = {
            (e["title"], e.get("date_start"))
            for e in events
        }

        # Fetch all existing events from the database.
        db_events = session.query(Event).all()

        # DELETE — remove events that no longer exist in the scraped data.
        for db_event in db_events:
            key = (db_event.title, db_event.date_start)

            if key not in new_event_keys:
                session.delete(db_event)

        # INSERT & UPDATE — add new events or update existing ones.
        for e in events:

            # Check if the event already exists in the database.
            exists = session.query(Event).filter_by(
                title=e["title"],
                date_start=e.get("date_start")
            ).first()

            if exists:
                # UPDATE — overwrite fields with latest scraped data.
                exists.image = e.get("image_url")
                exists.location = e.get("location")
                exists.price = e.get("price")
                exists.time_start = e.get("time_start")
                exists.city = e.get("city")
                exists.description = e.get("description")

            else:
                # INSERT — create a new event record.
                event = Event(
                    title=e["title"],
                    image=e.get("image_url"),
                    location=e.get("location"),
                    city=e.get("city"),
                    date_start=e.get("date_start"),
                    time_start=e.get("time_start"),
                    price=e.get("price"),
                    description=e.get("description")
                )

                session.add(event)

        # Commit all changes to the database.
        session.commit()
        print(f"Database synced. {len(events)} active events.")

    finally:
        # Always close the session.
        session.close()

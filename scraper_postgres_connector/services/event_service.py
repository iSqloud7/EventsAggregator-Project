from scraper_postgres_connector.db.database import Session
from scraper_postgres_connector.db.models import Event


def load_events_from_json(events: list):

    session = Session()

    try:

        new_event_keys = {
            (e["title"], e.get("date_start"))
            for e in events
        }

        db_events = session.query(Event).all()

        db_event_keys = {
            (e.title, e.date_start)
            for e in db_events
        }

        # DELETE
        for db_event in db_events:
            key = (db_event.title, db_event.date_start)

            if key not in new_event_keys:
                session.delete(db_event)

        # INSERT & UPDATE
        for e in events:

            exists = session.query(Event).filter_by(
                title=e["title"],
                date_start=e.get("date_start")
            ).first()

            if exists:
                exists.image = e.get("image_url")
                exists.location = e.get("location")
                exists.price = e.get("price")
                exists.time_start = e.get("time_start")
                exists.city = e.get("city")
                exists.description = e.get("description")

            else:
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

        session.commit()

        print(f"Database synced. {len(events)} active events.")

    finally:
        session.close()
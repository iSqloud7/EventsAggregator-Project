from db.database import Session
from db.models import Event, Wishlist


def load_events_from_json(events: list):
    session = Session()

    try:
        new_event_keys = {
            (e["title"], e.get("date_start"))
            for e in events
        }

        db_events = session.query(Event).all()

        # DELETE — remove events that no longer exist in the scraped data.
        for db_event in db_events:
            key = (db_event.title, db_event.date_start)

            if key not in new_event_keys:
                # Прво избриши ги wishlist записите за овој настан
                session.query(Wishlist).filter_by(event_id=db_event.id).delete()
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
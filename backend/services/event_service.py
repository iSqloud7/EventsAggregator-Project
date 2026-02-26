from backend.db.database import Session
from backend.db.models import Event


def load_events_from_json(events: list):
    session = Session()

    for e in events:
        exists = session.query(Event).filter_by(
            title=e["title"],
            date_start=e["date_start"]
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
    session.close()
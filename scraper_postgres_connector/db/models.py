from sqlalchemy import Column, Integer, String
from sqlalchemy.orm import declarative_base

# Base class for all SQLAlchemy models.
Base = declarative_base()


class Event(Base):
    __tablename__ = "events"

    id = Column(Integer, primary_key=True, autoincrement=True)

    # Event details.
    title = Column(String, nullable=False)
    image = Column(String, nullable=True)
    location = Column(String, nullable=True)
    price = Column(String, nullable=True)
    time_start = Column(String, nullable=True)
    date_start = Column(String, nullable=True)
    city = Column(String, nullable=True)
    description = Column(String, nullable=True)

    # Serialize event to dictionary (used for API responses).
    def to_dict(self):
        return {
            "id": self.id,
            "title": self.title,
            "image": self.image,
            "location": self.location,
            "city": self.city,
            "price": self.price,
            "date_start": self.date_start,
            "time_start": self.time_start,
            "description": self.description
        }

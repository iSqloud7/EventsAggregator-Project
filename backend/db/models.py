from sqlalchemy import Column, Integer, String, DateTime
from sqlalchemy.orm import declarative_base

Base = declarative_base()

class Event(Base):
    __tablename__ = "events"
    id       = Column(Integer, primary_key=True, autoincrement=True)
    title     = Column(String, nullable=False)
    image = Column(String, nullable=True)
    location   = Column(String, nullable=True)
    price  = Column(String, nullable=True)
    time_start     = Column(String, nullable=True)
    date_start = Column(String, nullable=True)
    city      = Column(String, nullable=True)
    description    = Column(String, nullable=True)
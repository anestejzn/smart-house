ALARM_MESS = "ALARM"
NORM_MESS = "NORMAL"

MESSAGES = {
    "CAMERA": {
        ALARM_MESS: [
            "Camera has detected a moving object!"
        ],
        NORM_MESS: [
            "No movement."
        ]
    },
    "AIR_CONDITIONER": {
        ALARM_MESS: [
            "The air conditioner has malfunctioned!",
            "The air conditioner's refrigerant is leaking!"
        ],
        NORM_MESS: [
            "The air conditioner has been turned on.",
            "The air conditioner has been turned off."
        ]
    },
    "TEMP_SENSOR": {
        ALARM_MESS: [
            "Room has reached critical low temperature!",
            "Room has reached critical high temperature!"
        ],
        NORM_MESS: [
            "Room temperature is at optimal level."
        ]
    },
    "SMOKE_SENSOR": {
        ALARM_MESS: [
            "Smoke has been detected!"
        ],
        NORM_MESS: [
            "No smoke found."
        ]
    },
    "WATER_SENSOR": {
        ALARM_MESS: [
            "Water has been detected on the building floor!"
        ],
        NORM_MESS: [
            "No water."
        ]
    },
    "AIR_SENSOR": {
        ALARM_MESS: [
            "Room air pollution is at critical level."
        ],
        NORM_MESS: [
            "Detected low amount micro particles in the air.",
            "No micro particles detected in the air.",
        ]
    },
    "WIN_LOCKED_SENSOR": {
        ALARM_MESS: [
            "The window has been broken! Intruder suspected!",
            "The window has been forcefully opened! Intruder suspected!"
        ],
        NORM_MESS: [
            "The window's lock is now on.",
            "The window's lock is now off."
        ]
    },
}


class MessageRequest:
    def __init__(self, text: str, date_time: str, dev_id: int, dev_type: str):
        self.message_text: str = text
        self.date_time: str = date_time
        self.dev_id: int = dev_id
        self.dev_type: str = dev_type

    def get_json(self):
        return {
            "messageText": self.message_text,
            "dateTime": self.date_time,
            "deviceId": self.dev_id,
            "deviceType": self.dev_type
        }
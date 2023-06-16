from datetime import datetime
from typing import List
import random

from message_encrypter import MessageEncrypter
from message_request import MessageRequest
from messages import MESSAGES, ALARM_MESS, NORM_MESS

class MessagesGenerator:
    MESS_TYPES = [ALARM_MESS, NORM_MESS]
    RAND_WEIGHTS = [0.25, 0.75]

    def __init__(self, dev_ids: List[int], dev_types: List[str]):
        self.dev_ids = dev_ids
        self.dev_types = dev_types
        self.encrypter = MessageEncrypter()

    def gen_next_iter_of_messages(self) -> List[MessageRequest]:
        return [
            MessageRequest(
                self.encrypter.encrypt(
                    f"Device {dev_type} with id:{dev_id} -> {random.choice(MESSAGES[dev_type][self.gen_rand_mess_type()])}"
                ),
                datetime.now().strftime("%Y-%m-%dT%H:%M:%S"),
                dev_id,
                dev_type,
            )
            for dev_id, dev_type in zip(self.dev_ids, self.dev_types)
        ]

    def gen_rand_mess_type(self) -> str:
        return random.choices(self.MESS_TYPES, self.RAND_WEIGHTS)[0]



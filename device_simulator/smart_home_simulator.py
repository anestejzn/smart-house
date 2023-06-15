import time
from typing import List

import schedule

from http_requester import HTTPRequester
from messages_generator import MessagesGenerator


def simulation(requester: HTTPRequester):
    dev_sim_data: (List[int], List[str]) = requester.get_device_ids_and_types()
    mess_gen = MessagesGenerator(dev_sim_data[0], dev_sim_data[1])
    requester.send_generated_messages(mess_gen.gen_next_iter_of_messages())


def main():
    requester = HTTPRequester()
    schedule.every(3).seconds.do(lambda: simulation(requester))

    while True:
        schedule.run_pending()
        time.sleep(2)


if __name__ == "__main__":
    main()

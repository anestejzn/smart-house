from typing import List, Any

import requests
import json

from message_request import MessageRequest


class HTTPRequester:
    _API_BASE_URL: str = "http://localhost:8080/"
    _EMAIL: str = "admin@gmail.com"
    _PASSWORD: str = "sifra1234A2@"

    def __init__(self):
        self.headers: {str: str}
        self._login()

    def _login(self):
        print("Logging in!")
        result = requests.post(
            f"{self._API_BASE_URL}auth/login-admin",
            json={"email": self._EMAIL, "password": self._PASSWORD},
            verify=False
        )

        self.headers = {
            "Authorization": f"Bearer {json.loads(result.text)['token']}",
            "Cookie": result.headers["Set-Cookie"],
        }

    def get_device_ids_and_types(self) -> (List[int], List[str]):
        print("Fetching active devices data!")
        result = requests.get(
            f"{self._API_BASE_URL}devices/deviceSimulationData",
            headers=self.headers,
            verify=False
        )
        dev_data: List[Any] = json.loads(result.text)
        return (
            list(map(lambda dev: dev['id'], dev_data)),
            list(map(lambda dev: dev['type'], dev_data))
        )

    def send_generated_messages(self, data: List[MessageRequest]):
        print("Sending generated messages!")
        requests.post(
            f"{self._API_BASE_URL}devices/saveDeviceMessages",
            headers=self.headers,
            json=list(map(lambda req: req.get_json(), data)),
            verify=False
        )
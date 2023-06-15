from Crypto.Cipher import AES
from Crypto.Util.Padding import pad
import base64


class MessageEncrypter:
    KEY: bytes  = b'hHsigei39d93j#ja'
    IV: bytes  = b'1234567890abcdef'

    def encrypt(self, mess: str) -> str:
        padded_message = pad(mess.encode('utf-8'), AES.block_size)
        cipher = AES.new(self.KEY, AES.MODE_CBC, self.IV)
        return base64.b64encode(cipher.encrypt(padded_message)).decode('utf-8')

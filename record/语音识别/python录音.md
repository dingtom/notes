···
import wave
from pyaudio import PyAudio, paInt16


def save_wave_file(filename, data):
    with wave.open(filename, 'wb') as wf:
        wf.setnchannels(channels)
        wf.setsampwidth(sampwidth)
        wf.setframerate(framerate)
        wf.writeframes(b"".join(data))


def my_record(filename):
    pa = PyAudio()
    stream = pa.open(format=paInt16,
                     channels=1,
                     rate=framerate,
                     input=True,
                     frames_per_buffer=NUM_SAMPLES)
    my_buf = []
    count = 0
    print('正在录音，请说话...')
    while count < TIME*8:  # 控制录音时间,每秒8个buffer
        string_audio_data = stream.read(NUM_SAMPLES)
        my_buf.append(string_audio_data)
        count += 1
    print('录音结束')
    save_wave_file(filename+".wav", my_buf)
    stream.close()


if __name__ == '__main__':
    NUM_SAMPLES = 2000  # frames_per_buffer
    channels = 1
    sampwidth = 2
    framerate = 16000
    name = input('请输入要保存的录音文件的名称')
    TIME = int(input('请输入要录音的时长'))
    my_record(name)

···

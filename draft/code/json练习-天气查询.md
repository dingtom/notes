```
# 解析天气接口，获取某一个城市的天气情况
import requests, json
class Weather(object):
    def __init__(self):
        self.ip_api = 'https://api.map.baidu.com/location/ip?ak=KHkVjtmfrM6NuzqxEALj0p8i1cUQot6Z'
        self.weather_api = 'http://api.map.baidu.com/telematics/v3/weather?location={}&output=json&ak=TueGDhCvwI6fOrQnLM0qmXxY9N0OkOiQ&callback=?'

    def get_current_city(self):
        """
        获取ip定位的城市信息
        :return:
        """
        response = requests.get(self.ip_api)
        city_dict = json.loads(response.text)
        city = city_dict['content']['address_detail']['city']
        return city

    def get_city_weather(self, city_name):
        """
        根据城市名称，获取城市天气。
        :param city_name: 城市名称
        :return:
        """
        url = self.weather_api.format(city_name)
        response = requests.get(url)
        json_dict = json.loads(response.text)

        results_dict = json_dict['results'][0]
        weather_datas = results_dict['weather_data']
        print('------------------------------------')
        for weather_data in weather_datas:
            print('日期：{}；天气：{}；风力：{}；温度：{}'.format(weather_data['date'], weather_data['weather'], weather_data['wind'], weather_data['temperature']))
        print('------------------------------------')

    def start_query(self):
        city_name = self.get_current_city()
        self.get_city_weather(city_name)

        while True:
            city = input('请输入查询城市(Q退出)：')
            if city == 'Q':
                break
            self.get_city_weather(city)

if __name__ == '__main__':
    weather = Weather()
    weather.start_query()
```

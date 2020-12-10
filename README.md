Как запустить проект
1. Необходимо что было установлено java 8, Gradle и Docker
2. Запускаем команду gradle clean build docker
3. Заходим в папку docker и выполняем команду docker-compose up -d
4. Прлиожение будет доступон по адресу http://127.0.0.1:8087/accounts
5. Команды для работы с приложением 
   get http://127.0.0.1:8087/accounts/all получить весь список акаутнов
   
   get http://127.0.0.1:8087/accounts/123 где 123 номер акаунта
   если счет 123 отсутсвует то появится сообщение 
	{
		"className": "AccountDoesNotExists",
		"date": "2020-12-10T09:26:49.071+00:00",
		"errorDescription": "Account does not exists"
	}
	
   post http://127.0.0.1:8087/accounts/inc добавить деньги к балансу счета где body выглядет как
   {
        "accountId": "123",        
        "amount": 125
    }
    если счет 123 отсутсвует то он создасться 
	post http://127.0.0.1:8087/accounts/dec списать деньги со счета где body выглядет как
   {
        "accountId": "1231",        
        "amount": 125
    }
	если счет 1231 отсутсвует то появится сообщение 
	{
		"className": "AccountDoesNotExists",
		"date": "2020-12-10T09:26:49.071+00:00",
		"errorDescription": "Account does not exists"
	}
	
	если денег на счету недостаточно то появится сообщение 
	{
		"className": "NotEnoughMoneyException",
		"date": "2020-12-10T09:29:25.528+00:00",
		"errorDescription": "Not Enough Money"
	}
	
	если все прошло нормально то вернется ответ с http кодом 200

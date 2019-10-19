# Fyre
## Api
Сервер возвращает json. В json обязательно есть поле status которое показывает успешность выполнение операции.
Так же может появится поле obj. В нём будет находится дополнительная информация к ответу(к примеру список рецептов).
* /test - проверка подключения к серверу;
* /login?login={}&password={} - проверка авторизации пользователя. Результат авторизации будет сохранен как булевое значение в дополнительной информации к ответ;
* /login/moderator?login={}&password={} - проверка авторизации модератора. Результат авторизации будет сохранен как булевое значение в дополнительной информации к ответу.
* /registration?login={}&password={}&name={}&surname={}&email={} - регистрация пользователя. Результат регистрации будет сохранен как булевое значение в дополнительной информации к ответу.
* /registration/moderator?name={}?login={}&password={} - регистрация модератора. Результат регистрации будет сохранен как булевое значение в дополнительной информации к ответу.
* /add/recipe?recipeName={}&recipeComposition={}&cookingSteps={}&publicationDate={}&selectedTypes={} - добавление рецепта. Результат добавления будет сохранен как булевое значение в дополнительной информации к ответу.
* /add/type?typeName={}&description={} - добавление типа. Результат добавления будет сохранен как булевое значение в дополнительной информации к ответу.
* /change/status?userLogin={} - изменение статуса пользователя. Результат изменения будет сохранен как булевое значение в дополнительной информации к ответу.
* /delete/recipe?recipeName={} - удаление рецепта по имени. Результат удаления будет сохранен как булевое значение в дополнительной информации к ответу.
* /select/types - получение информации о типах рецептов. Результат будет сохранен как список объектов типа Type в дополнительной информации к ответу.
* /select/users - получение информации о пользователях. Результат будет сохранен как список объектов типа Person в дополнительной информации к ответу.

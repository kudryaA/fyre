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

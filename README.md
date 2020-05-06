# One-click English - Aplikacja do nauki języka angielskiego

## Spring Boot, Angular

Pozwala na automatyczne generowanie testów językowych otwartych oraz jednokrotnego wyboru. Generowane testy składają się wyłącznie ze słów zgromadzonych przez użytkownika oraz danych powiązanych z zapisanymi słowami. Wszystkie kategorie testów mają zaimplementowany mechanizm sprawdzania poprawności udzielanych odpowiedzi.

Celem takiego rozwiązania jest umożliwienie gromadzenia obcych angielskich słówek w prosty sposób i ułatwienie ich późniejszej nauki.

Docelowo aplikacja posiadać będzie także rozszerzenie do przeglądarki umożliwiające zapis słów pochodzących z dowolnej witryny anglojęzycznej poprzez ich zaznaczenie i użycie skrótu klawiszowego.

## Logowanie
![logowanie](https://user-images.githubusercontent.com/56847749/81172664-f36f8c00-8f9e-11ea-9e78-9c968d5d2930.png)
## Rejestracja
![rejestracja](https://user-images.githubusercontent.com/56847749/81172744-1c901c80-8f9f-11ea-84c9-6c823bb4274b.png)
## Zapisywanie i usuwanie słów
Angielskie słowa podczas zapisu pobierają z zewnętrznych API swoje polskie tłumaczenie, listę definicji oraz przykładowe zdania z ich użyciem. Przypisywane są użytkownikom w tabeli kluczy obcych w relacji @ManyToMany.
W sytuacji, w której inny użytkownik zapisze istniejące słowo zostanie ono do niego przypisane z bazy danych.
Pozwala to na ograniczenie wysyłania żądań do API. W przypadku usunięcia słowa przez użytkownika usuwany jest wyłącznie rekord przechowujący klucz obcy użytkownika i słowa.

![moje słówka](https://user-images.githubusercontent.com/56847749/81172760-27e34800-8f9f-11ea-869e-f098e2158f63.png)
## Testy z czasownikami
Generowanie testów językowych składających się z przykładowych zdań, w których używany jest czasownik angielski zapisany przez użytkownika oraz z czterech odpowiedzi zawierających różne odmiany tego czasownika. 
Obecnie funkcjonalność ta nie jest jeszcze ukończona z powodu trudności dostępu do API które umożliwiałoby zwracanie wszystkich form czasownika po wysłaniu żądania z dowolną jego formą. 

![czasowniki](https://user-images.githubusercontent.com/56847749/81172783-36316400-8f9f-11ea-9a30-86b5d40e9fb6.png)
## Testy z losowymi słowami
Generowanie testów językowych składających się z przykładowych zdań, w których używane jest słowo angielskie zapisane przez użytkownika oraz z czterech odpowiedzi zawierających losowo zwrócone słowa z bazy danych.

![losowe z odp](https://user-images.githubusercontent.com/56847749/81172820-48ab9d80-8f9f-11ea-910d-b4b73b81cd10.png)
## Testy z definicjami
Generowanie testów językowych składających się z definicji słów zapisanych przez użytkownika oraz formularzy odpowiedzi. Możliwe jest także podglądnięcie podpowiedzi w formie polskiego tłumaczenia definicji.

![definicje podp](https://user-images.githubusercontent.com/56847749/81172852-582ae680-8f9f-11ea-9884-a7a1b4ac4797.png)
![definicje z odp](https://user-images.githubusercontent.com/56847749/81172893-68db5c80-8f9f-11ea-8678-363b80b56b6f.png)
## Testy z przykładowymi zdaniami
Generowanie testów językowych składających się z przykładowych zdań, w których używane jest słowo angielskie zapisane przez użytkownika oraz formularzy odpowiedzi. Możliwe jest także podglądnięcie podpowiedzi w formie polskiego tłumaczenia brakującego słowa.

![zdania podp](https://user-images.githubusercontent.com/56847749/81172935-7a246900-8f9f-11ea-8083-7cad6367a612.png)
![zdania z odp](https://user-images.githubusercontent.com/56847749/81172953-827ca400-8f9f-11ea-8e39-3b1584fa4c5d.png)
## Fiszki
Obecnie ta funkcjonalność nie jest jeszcze zaimplementowana. 
- Generowanie fiszki na podstawie losowego słowa pobieranego z bazy danych użytkownika.
- Możliwość wyboru języka angielski-polski lub polski-angielski.
- Formularz odpowiedzi i sprawdzanie poprawności wpisanego tłumaczenia słówka.
## Błędny URL
![not found](https://user-images.githubusercontent.com/56847749/81173033-a9d37100-8f9f-11ea-9ec5-d8f77a76e450.png)

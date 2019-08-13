# poker-tools

## About Poker Tools API

An API that provides endpoints for poker games interfaces.

## Key Features

Current version can evaluate two players cards hand to obtain the winner.

## How to run

```sh
git clone https://github.com/paulohva/poker-tools.git
```
```sh
./mvnw spring-boot:run
```
or if you have Maven 3 installed:
```sh
./mvnw sprint:boot:run
```
Endpoint that allows POST requests:
```http
http://localhost:8080/api/game/evaluatehands
```

* Use a tool that can create HTTP requests, like Postman (https://www.getpostman.com/)
* Create a POST request putting a JSON as Body, like the example bellow:

```json
{
    "playerOne": {
        "playerName": "Paulo",
        "cards": [
            {
                "kind": "D",
                "value": "4"
            },
            {
                "kind": "S",
                "value": "4"
            },
            {
                "kind": "D",
                "value": "3"
            },
            {
                "kind": "S",
                "value": "5"
            },
            {
                "kind": "D",
                "value": "6"
            }
        ]
    },
    "playerTwo": {
        "playerName": "Ju",
        "cards": [
            {
                "kind": "H",
                "value": "A"
            },
            {
                "kind": "C",
                "value": "A"
            },
            {
                "kind": "H",
                "value": "3"
            },
            {
                "kind": "C",
                "value": "4"
            },
            {
                "kind": "H",
                "value": "5"
            }
        ]
    }
}
```
## JSON Format

Each card uses the following format:
* "kind":
  * "H" for Hearts
  * "C" for Clubs
  * "D" for Diamonds
  * "S" for Spades
* "value":
  * from 1 to 10, just the number
  * "J" for Jack
  * "Q" for Queen
  * "K" for King
  * "A" for Ace

## Output format

```json
{
    "playerName": "Ju",
    "cards": [
        {
            "value": "A",
            "kind": "H"
        },
        {
            "value": "A",
            "kind": "C"
        },
        {
            "value": "5",
            "kind": "H"
        },
        {
            "value": "4",
            "kind": "C"
        },
        {
            "value": "3",
            "kind": "H"
        }
    ],
    "highRank": "ONE_PAIR"
}
```

* "playerName": the winner's name
* "cards": highest hand
* "highRank": category of the highest hand

## Run JUnit tests

There is a suit of JUnit tests that can be runned for the algorithms on service layer.

```sh
./mvnw test
```

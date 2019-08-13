# poker-tools

## About Poker Tools API

An API that provides endpoints for poker games

## Key Features

Current version can evaluate two players card hand to obtain the winner

## How to run

```sh
git clone https://github.com/paulohva/poker-tools.git
```
```sh
./mvnw sprint:boot:run
```
or if you have Maven 3 installed:
```sh
./mvnw sprint:boot:run
```
* Use a tool that can create HTTP requests, like Postman (https://www.getpostman.com/)
* Define a JSON like the example above:
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
                "value": "2"
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
                "value": "2"
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
** "H" for Hearts
** "C" for Clubs
** "D" for Diamonds
** "S" for Spades
* "value":
** from 1 to 10, just the number
** "J" for Jack
** "Q" for Queen
** "K" for King
** "A" for Ace

## Output format

```
{
    "playerName": "Paulo",
    "highRank": "STRAIGHT"
}
```
* "playerName": the winner's name
* "highRank": name of category that wins




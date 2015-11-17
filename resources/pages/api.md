# API

A word list API is available; the current version consists of a single endpoint,
which can be requested to generate word lists:

## Endpoints (v1)

API Root: `/api/v1`

### GET `/generate`

Retrieves a set of wors; accepts the following GET parameters:

- `num-words` (integer, default: 4) Number of words you'd like to receive
- `min-chars` (integer, default: 5) Minimum number of characters a word may
  contain
- `max-chars` (integer, default: 8) Maximum number of characters a word may
  contain

#### Example

`curl https://mkwords.fardog.io/api/v1/generate?num-words=5`

Response:

```json
{
  "ok": true,
  "response": "pictured menace amino canister twangs",
  "candidate-count": 70806
}
```

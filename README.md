# Picky Nickname Validator

## Goal

Build a tiny full-stack app where the server may reject a user's request, but responds with constructive alternatives. The UI should treat this as a conversation, not a failure.

## Time Expectation

1–2 hours. Please keep scope tight as it makes it easy on you and us.

## What You're Building

A "Nickname Chooser" with some opinionated rules.

Users attempt to set a nickname. The server either accepts it or responds with a negotiation explaining why it doesn't quite work and offering better options.

### Nickname Rules

- Must contain at least one letter `o` (other vowels are fine)
- Cannot contain the word `cat` (other animals are fine)
- Cannot end with a single digit number (other longer numbers are fine, as is no number)
- Must not already be taken (small in-memory set is fine)

### Validation Behavior

If the nickname passes all rules, the server stores it as the current nickname.

If it fails, the server returns a structured negotiation response containing:
- A machine-readable reason code (or codes)
- A human-readable explanation
- 2–4 suggested alternatives that attempt to "smooth" the name

Suggestions can be generated with simple heuristics that remove the offending part.

## Backend Requirements (Java)

Just one endpoint (exact path is up to you):

**POST** - attempt to set nickname

Request body:
```json
{ "nickname": "string" }
```

Response is either:
- **Accepted:** nickname stored and returned
- **Negotiated:** rejection payload with reasons and suggestions

In-memory storage is sufficient. No database.

## Frontend Requirements (Svelte)

A single page is enough.

### UI Flow

- Input field to enter nickname
- Submit button
- On success: show a simple confirmation
- On negotiation: display the explanation and render suggestions as buttons
- Clicking a suggestion immediately attempts to set it

Negotiation should be a visible state in the UI, not an error banner.

## Project Constraints

- Minimal styling is fine
- No auth, no persistence
- Avoid overengineering
- Tests are optional

## What We're Looking For

- Clean consistent API modeling
- Thoughtful UI states and transitions
- Clear readable code
- Incremental commits and a pull request

## Deliverables

A pull request to the picky-nickname-validator repository

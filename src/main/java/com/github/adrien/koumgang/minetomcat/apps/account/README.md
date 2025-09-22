

# Account

## Collections & document shapes

```json

{
  "_id": { "$oid": "..." },
  "userId": { "$oid": "..." },
  "name": "Revolut",
  "type": "bank",
  "currency": "EUR",
  "startingBalance": { "$numberDecimal": "0" },
  "isArchived": false,
  "createdAt": { "$date": "2025-09-19T08:00:00Z" }
}

```

type: cash|bank|card|wallet|investment

**Indexes**:

- { userId: 1, name: 1 } (unique per user)
- { userId: 1, isArchived: 1 }




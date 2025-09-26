

# Category


## Collections & document shapes

```json

{
  "_id": { "$oid": "..." },
  "userId": { "$oid": "..." },
  "name": "Groceries",
  "type": "expense",
  "parentId": { "$oid": "..." }
}

```

type: expense|income

**Indexes**:

- unique { userId: 1, name: 1, type: 1 }
- { userId: 1, parentId: 1 }


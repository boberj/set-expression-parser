set-expression-parser
=====================

PEG parser that parses set expressions. It recognizes the set operations union (`|`), intersection (`&`) and difference (`-`).

Example
-------
Given the sets

```
fruits = [apple, orange, strawberry]
sweets = [chocolate, liquorice]
deliciousness = [strawberry, chocolate, whipped cream]
```

and the expression `(fruits | sweets) & deliciousness` the resulting set is `[strawberry, chocolate]`.
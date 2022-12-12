Task #1
Create simple REST API for library using node.js with such endpoints:
- [POST] endpoint/books (add book to library)
- [GET] endpoint/books (list books)
- [GET] endpoint/books/:id (list book)
- [PUT] endpoint/books/:id (update book information)
- [DELETE] endpoint/books/:id (remove book from library)
  Schema for book should be as follows:
  const bookSchema = new Schema({
  name: String,
  author: String,
  year: String,
  available: Number
  });

Task #2
Create simple test solution and write end user tests for REST API that was created in task #1.
You can use your preferred programming language and frameworks.
We will evaluate:
- Understandability of tests
- Development patterns and coding style
- Test solution design patterns
- User friendliness
- How easy to run tests
- How easy to understand failures
- How easy is to




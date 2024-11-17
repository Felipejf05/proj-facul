import api from "./api";

const BookService = {
  getAllBooks: () => api.get("/books"),
  getBookById: (id) => api.get(`/books/${id}`),
  createBook: (book) => api.post("/books", book),
  updateBook: (id, book) => api.put(`/books/${id}`, book),
  deleteBook: (id) => api.delete(`/books/${id}`),
};

export default BookService;

import api from "./api";

const UserService = {
  getAllUsers: () => api.get("/users"),
  getUserById: (id) => api.get(`/users/${id}`),
  createUser: (user) => api.post("/users", user),
  updateUser: (id, user) => api.put(`/users/${id}`, user),
  deleteUser: (id) => api.delete(`/users/${id}`),
};

export default UserService;

export default interface User {
  id: number | null;
  username: string;
  firstName: string;
  lastName: string;
  profileImage: string | null;
  role: string | null;
}

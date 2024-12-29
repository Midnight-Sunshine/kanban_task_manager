import { User } from "./User";

export class AuthenticationResponse {
    user: User;
    message: string;
  
    constructor(user: User, message: string) {
      this.user = user;
      this.message = message;
    }
  }
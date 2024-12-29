import { User } from "./User";

export class Board {
    constructor(
      public id: number,
      public name: string,
      public description: string,
      public dateCreation: Date,
      public user:User
    ) {}
  }
  
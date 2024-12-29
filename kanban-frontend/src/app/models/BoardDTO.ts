export class BoardDTO {
  name: string;
  description: string;
  dateCreation: Date;
  userId?: number; // Optional userId

  constructor(
    name: string,
    description: string,
    dateCreation: Date,
    userId?: number
  ) {
    this.name = name;
    this.description = description;
    this.dateCreation = dateCreation;
    this.userId = userId; // Initialize userId if provided
  }
}

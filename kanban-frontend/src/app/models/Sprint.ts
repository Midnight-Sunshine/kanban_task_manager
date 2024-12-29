import { Task } from "./Task";

export class Sprint { 
    constructor(
    public  id: number,
    public  name: string,
    public startDate: Date,
    public  endDate: Date,
    ) {}
  }
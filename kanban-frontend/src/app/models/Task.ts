import { Board } from "./Board";
import { User } from "./User";
import { TaskStatus } from "./TaskStatus";
import { Sprint } from "./Sprint";

export class Task {  
    constructor(
    public  id: number,
    public  name: string,
    public  description: string,
    public  status: TaskStatus,
    public  estimation: number,
    public  board: Board,
    public  users: User[],
    public  sprints: Sprint[]
    ) {}
  }
  

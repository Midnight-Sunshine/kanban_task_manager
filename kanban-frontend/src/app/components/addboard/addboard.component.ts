import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { BoardDTO } from 'src/app/models/BoardDTO';
import { BoardService } from 'src/app/services/board.service';
import { LoginService } from 'src/app/services/auth.service'; // Import LoginService

@Component({
  selector: 'app-addboard',
  templateUrl: './addboard.component.html',
  styleUrls: ['./addboard.component.css']
})
export class AddboardComponent implements OnInit {
  boardForm: FormGroup;
  successMessage: string = "";
  errorMessage: string = "";
  userId: number | null = null; // Store the userId

  constructor(
    private formBuilder: FormBuilder,
    private boardService: BoardService,
    private loginService: LoginService, // Inject LoginService
    private router: Router
  ) {
    this.boardForm = this.formBuilder.group({
      name: ['', Validators.required],
      description: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    // Retrieve the userId from LoginService
    this.userId = this.loginService.getUserId(); // Assuming it returns null if not logged in
    if (!this.userId) {
      this.errorMessage = "User is not logged in.";
    }
  }

  onSubmit() {
    if (this.boardForm.valid && this.userId) {
      const boardData: BoardDTO = new BoardDTO(
        this.boardForm.value.name,
        this.boardForm.value.description,
        new Date(),
        this.userId // Pass the userId
      );

      this.boardService.addBoard(boardData).subscribe(
        response => {
          this.successMessage = "Board added successfully.";
          this.boardForm.reset();
          this.router.navigate(['/boards']);
        },
        error => {
          this.errorMessage = "Failed to add board.";
          console.error('Error:', error);
        }
      );
    } else if (!this.userId) {
      this.errorMessage = "Unable to determine the logged-in user.";
    }
  }
}

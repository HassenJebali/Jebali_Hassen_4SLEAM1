import { Component, OnInit } from '@angular/core';
import { EnrollmentService, Enrollment } from '../services/enrollment.service';

@Component({
  selector: 'app-enrollment-list',
  templateUrl: './enrollment-list.component.html',
  styleUrls: ['./enrollment-list.component.css']
})
export class EnrollmentListComponent implements OnInit {
  enrollments: Enrollment[] = [];
  errorMessage: string = '';

  constructor(private enrollmentService: EnrollmentService) { }

  ngOnInit(): void {
    this.getAllEnrollments();
  }

  getAllEnrollments(): void {
    this.enrollmentService.getAllEnrollments().subscribe({
      next: (data) => this.enrollments = data,
      error: (err) => this.errorMessage = err.message
    });
  }
}

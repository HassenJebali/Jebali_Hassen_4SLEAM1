import { Component, OnInit } from '@angular/core';
import { Enrollment, EnrollmentService } from 'src/app/services/enrollment.service';

@Component({
  selector: 'app-enrollments',
  templateUrl: './enrollments.component.html',
  styleUrls: ['./enrollments.component.css']
})
export class EnrollmentsComponent implements OnInit {

  enrollments: Enrollment[] = [];

  constructor(private enrollmentService: EnrollmentService) { }

  ngOnInit(): void {
    this.loadEnrollments();
  }

  loadEnrollments(): void {
    this.enrollmentService.getAllEnrollments().subscribe(
      data => this.enrollments = data,
      error => console.error(error)
    );
  }

  deleteEnrollment(id: number): void {
    this.enrollmentService.deleteEnrollment(id).subscribe(() => {
      this.enrollments = this.enrollments.filter(e => e.idEnrollment !== id);
    });
  }
}

import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {HttpEventType, HttpResponse} from '@angular/common/http';
import {UploadFileService} from '../../../@core/services/uploadFileService.service';
import {Observable} from 'rxjs';

class RequestOptions {
  constructor(param: { headers: Headers }) {

  }

}

@Component({
  encapsulation: ViewEncapsulation.None,
  selector: 'ngx-image-management',
  styleUrls: ['./image-management.component.scss'],
  templateUrl: './image-management.component.html',
})
export class ImageManagementComponent implements OnInit  {
  selectedFiles: FileList;
  currentFile: File;
  progress = 0;
  message = '';

  fileInfos: Observable<any>;

  constructor(private uploadService: UploadFileService) { }
  ngOnInit() {
    this.fileInfos = this.uploadService.getFiles();
  }
  upload() {
    this.progress = 0;

    this.currentFile = this.selectedFiles.item(0);
    // this.uploadService.upload(this.currentFile).subscribe(
    //   (res) => {
    //     console.log(res);
    //     this.message = res.body.data ;
    //   },
    //   (error) => {
    //     this.progress = 0;
    //     this.message = 'Could not upload the file!';
    //     this.currentFile = undefined;
    //     // this.isLoad = false;
    //   },
    //   // () => this.isLoad = false,
    //   );

    this.selectedFiles = undefined;
  }

  selectFile(event) {
    this.selectedFiles = event.target.files;
  }


  // title = 'File-Upload-Save';
  // selectedFiles: FileList;
  // currentFileUpload: File;
  // progress: { percentage: number } = {percentage: 0};
  // selectedFile = null;
  // changeImage = false;
  //
  // constructor(private uploadService: UploadFileService) {
  // }
  //
  // downloadFile() {
  //   const link = document.createElement('a');
  //   link.setAttribute('target', '_blank');
  //   link.setAttribute('href', '_File_Saved_Path');
  //   link.setAttribute('download', 'file_name.pdf');
  //   document.body.appendChild(link);
  //   link.click();
  //   link.remove();
  // }
  //
  // change($event) {
  //   this.changeImage = true;
  // }
  //
  // changedImage(event) {
  //   this.selectedFile = event.target.files[0];
  // }
  //
  // upload() {
  //   this.progress.percentage = 0;
  //   this.currentFileUpload = this.selectedFiles.item(0);
  //   this.uploadService.pushFileToStorage(this.currentFileUpload).subscribe(event => {
  //       if (event.type === HttpEventType.UploadProgress) {
  //         this.progress.percentage = Math.round(100 * event.loaded / event.total);
  //       } else if (event instanceof HttpResponse) {
  //         alert('File Successfully Uploaded');
  //       }
  //       this.selectedFiles = undefined;
  //     }
  //   );
  // }
  //
  // selectFile(event) {
  //   this.selectedFiles = event.target.files;
  // }
}

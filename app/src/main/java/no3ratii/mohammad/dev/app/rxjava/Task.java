package no3ratii.mohammad.dev.app.rxjava;

import androidx.lifecycle.LiveData;

public class Task {
  private String description;
  private boolean isComplete;
  private int priority;
  private LiveData<String> dataString;

  public Task(String description, boolean isComplete, int priority) {
    this.description = description;
    this.isComplete = isComplete;
    this.priority = priority;
  }

  //----------------------------------------------------------
  public LiveData<String> getDataString() {
    return dataString;
  }

  public void setDataString(LiveData<String> dataString) {
    this.dataString = dataString;
  }
  //-------------------------------------------------------------


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isComplete() {
    return isComplete;
  }

  public void setComplete(boolean complete) {
    isComplete = complete;
  }

  public int getPriority() {
    return priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }
}

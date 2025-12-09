package com.example.application.examplefeature.ui;

import com.example.application.examplefeature.Task;
import com.example.application.examplefeature.TaskService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import static com.vaadin.flow.spring.data.VaadinSpringDataHelpers.toSpringPageRequest;


@Route("")
@PageTitle("Task List")
@Menu(order = 0, icon = "vaadin:clipboard-check", title = "Task List")
class TaskListView extends Main {

    private final TaskService taskService;
    final Grid<Task> taskGrid;

    public TaskListView(TaskService taskService) {
        this.taskService = taskService;
        taskGrid = new Grid<>();
        taskGrid.setItems(query -> taskService.list(toSpringPageRequest(query)).stream());
        add(taskGrid);
    }


}

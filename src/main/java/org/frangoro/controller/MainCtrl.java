package org.frangoro.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainCtrl {

  @ResponseBody
  public String index() {
    return "Video Game Rent System";
  }

}

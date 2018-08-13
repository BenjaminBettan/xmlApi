Feature: test xpath du scenario 2

#<?xml version="1.0" encoding="UTF-8"?>
#<x1>
#  <x2/>
#  <x2/>
#</x1>

  Scenario Outline: test xpath du scenario 2
    Given je charge scenario 2
    When je cherche <xpath>
    Then je dois trouver : <answer> entite

  Examples:
    | xpath           | answer |
    | "/x1/x2/"       |  2     |

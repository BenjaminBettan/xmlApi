Feature: 004_xpathComplexe

#@see test.xml

  Scenario Outline: test xpath du scenario 3
    Given je charge "test.xml"
    When je cherche <xpath>
    Then je dois trouver : <answer> entite

  Examples:
    | xpath                                                                    | answer |
    | "/project/dependencies/dependency/dependency/groupId[@test="toto"]/../groupId/"  |  1     |
    

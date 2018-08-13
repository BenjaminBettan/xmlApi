Feature: test du scenario 1

  # <?xml version="1.0" encoding="UTF-8" ?><x><x2><y2></y2></x2></x>
  # XMLEntity [id=0, level=0, tag=x, data=, leaf=false, isChildOf=-1, attributes=null, isFatherOf=[2]]

  Scenario Outline: Today is or is not Friday
    Given je charge scenario 1 et je cherche <xpath>
    Then je dois trouver : <answer>

  Examples:
    | xpath       | answer |
    | "/x/x2/y2/" |  1     |
#    | "/x/x2/"    |  1     |    
Feature: test du scenario 1

#XMLEntity [id=0, level=0, tag=x1, data=, leaf=false, isChildOf=-1, attributes=null, isFatherOf=[2]]
#XMLEntity [id=2, level=1, tag=x2, data=, leaf=false, isChildOf=0, attributes=null, isFatherOf=[1]]
#XMLEntity [id=1, level=2, tag=y1, data=, leaf=false, isChildOf=2, attributes=null, isFatherOf=[3]]
#XMLEntity [id=3, level=1, tag=y2, data=, leaf=true, isChildOf=1, attributes=null, isFatherOf=null]
#<?xml version="1.0" encoding="UTF-8" ?><x1><x2><y1><y2></y2></y1></x2></x1>


  Scenario Outline: Today is or is not Friday
    Given je charge scenario 1 et je cherche <xpath>
    Then je dois trouver : <answer>

  Examples:
    | xpath           | answer |
    | "/x1/x2/y1/y2/" |  1     |
#    | "/x/x2/"    |  1     |    
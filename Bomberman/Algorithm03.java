package ru.codebattle.client;

import ru.codebattle.client.api.*;
import java.util.ArrayList;
import java.util.List;

public class Algorithm03{
    private GameBoard gameBoard;
    private BoardPoint hero;
    private List<BoardPoint> bombs;
    private int radius_act = 3;
    private boolean up = true;
    private boolean right = true;
    private boolean down = true;
    private boolean left = true;
    private boolean act_up = false;
    private boolean act_right = false;
    private boolean act_down = false;
    private boolean act_left = false;
    private boolean act = false;

    public Algorithm03(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        this.hero = gameBoard.findElement(BoardElement.BOMBERMAN);
        this.bombs = new ArrayList<>();
//        System.out.println(gameBoard.amIDead());
//        System.out.println(hero);
//        System.out.println(gameBoard.size());
    }

    public boolean check_repeat(BoardPoint bomb) {
        if (bombs.indexOf(bomb) == -1) {
            bombs.add(bomb);
            return true;
        } else {
            return false;
        }
    }

    public boolean check_point(BoardPoint point) {
        if (obstacles(point)) {
            if (check_bombs(point)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean check_bombs(BoardPoint point) {
        boolean flag_up = check_up(point);
        if (!flag_up) return false;
        boolean flag_right = check_right(point);
        if (!flag_right) return false;
        boolean flag_down = check_down(point);
        if (!flag_down) return false;
        boolean flag_left = check_left(point);
        if (!flag_left) return false;
        return true;
    }

    public boolean check_up(BoardPoint p) {
        boolean flag_up = true;
        BoardPoint point;
        BoardElement element;
        for (int i = 1; i <= 3; i++) {
            point = p.shiftTop(i);
            element = gameBoard.getElementAt(point);
            if (element == BoardElement.WALL ||
                    element == BoardElement.DESTROY_WALL) {
                flag_up = true;
                break;
            }
            if (element == BoardElement.BOMB_TIMER_1) {
                flag_up = false;
                break;
            }
            if (element == BoardElement.BOMB_TIMER_2 ||
                    element == BoardElement.BOMB_TIMER_3 ||
                    element == BoardElement.BOMB_TIMER_4) {
                if (check_repeat(point)) {
                    flag_up = check_bombs(point);
                } else {
                    flag_up = true;
                    //break;
                }
            }
        }
        return flag_up;
    }

    public boolean check_right(BoardPoint p) {
        boolean flag_right = true;
        BoardPoint point;
        BoardElement element;
        for (int i = 1; i <= 3; i++) {
            point = p.shiftRight(i);
            element = gameBoard.getElementAt(point);
            if (element == BoardElement.WALL ||
                    element == BoardElement.DESTROY_WALL) {
                flag_right = true;
                break;
            }
            if (element == BoardElement.BOMB_TIMER_1) {
                flag_right = false;
                break;
            }
            if (element == BoardElement.BOMB_TIMER_2 ||
                    element == BoardElement.BOMB_TIMER_3 ||
                    element == BoardElement.BOMB_TIMER_4) {
                if (check_repeat(point)) {
                    flag_right = check_bombs(point);
                } else {
                    flag_right = true;
                   // break;
                }
            }
        }
        return flag_right;
    }

    public boolean check_down(BoardPoint p) {
        boolean flag_down = true;
        BoardPoint point;
        BoardElement element;
        for (int i = 1; i <= 3; i++) {
            point = p.shiftBottom(i);
            element = gameBoard.getElementAt(point);
            if (element == BoardElement.WALL ||
                    element == BoardElement.DESTROY_WALL) {  //|| element == BoardElement.MEAT_CHOPPER || element == BoardElement.OTHER_BOMBERMAN
                flag_down = true;
                break;
            }
            if (element == BoardElement.BOMB_TIMER_1) {
                flag_down = false;
                break;
            }
            if (element == BoardElement.BOMB_TIMER_2 ||
                    element == BoardElement.BOMB_TIMER_3 ||
                    element == BoardElement.BOMB_TIMER_4) {
                if (check_repeat(point)) {
                    flag_down = check_bombs(point);
                } else {
                    flag_down = true;
                   // break;
                }
            }
        }
        return flag_down;
    }

    public boolean check_left(BoardPoint p) {
        boolean flag_left = true;
        BoardPoint point;
        BoardElement element;
        for (int i = 1; i <= 3; i++) {
            point = p.shiftLeft(i);
            element = gameBoard.getElementAt(point);
            if (element == BoardElement.WALL ||
                    element == BoardElement.DESTROY_WALL) {  //|| element == BoardElement.MEAT_CHOPPER || element == BoardElement.OTHER_BOMBERMAN
                flag_left = true;
                break;
            }
            if (element == BoardElement.BOMB_TIMER_1) {
                flag_left = false;
                break;
            }
            if (element == BoardElement.BOMB_TIMER_2 ||
                    element == BoardElement.BOMB_TIMER_3 ||
                    element == BoardElement.BOMB_TIMER_4) {
                if (check_repeat(point)) {
                    flag_left = check_bombs(point);
                } else {
                    flag_left = true;
                    //break;
                }
            }
        }
        return flag_left;
    }

    public void check_all_points(BoardPoint point) {
        up = check_point(point.shiftTop());
        right = check_point(point.shiftRight());
        down = check_point(point.shiftBottom());
        left = check_point(point.shiftLeft());
    }

    public TurnAction move() {
        if (!gameBoard.amIDead()){
            if (hero == null /*&& gameBoard.findElement(BoardElement.BOMB_BOMBERMAN) != null*/) {
                this.hero = gameBoard.findElement(BoardElement.BOMB_BOMBERMAN);
            }
            check_all_points(hero);
            check_all_act(hero);
            if (!up && !right && !down && !left) {
                test(); // Не уверен в этом, если что закомить
                return new TurnAction(act, Direction.STOP);
            } else {
                return new TurnAction(act,random());
            }
        }else{
            return new TurnAction(false, Direction.STOP);
        }
    }

    public void test() {
        BoardElement bomb = BoardElement.BOMB_TIMER_1;
        if (gameBoard.getElementAt(hero.shiftTop()) == bomb ||
                gameBoard.getElementAt(hero.shiftRight()) == bomb ||
                gameBoard.getElementAt(hero.shiftBottom()) == bomb ||
                gameBoard.getElementAt(hero.shiftLeft()) == bomb) {
            act = false;
        }
    }

    public Direction random() {
        ArrayList<Direction> list = new ArrayList<>();
        if (up) list.add(Direction.UP);
        if (right) list.add(Direction.RIGHT);
        if (down) list.add(Direction.DOWN);
        if (left) list.add(Direction.LEFT);
        int random = (int) (Math.random() * list.size());
        return list.get(random);
    }

    public boolean obstacles(BoardPoint point) {
        BoardElement element = gameBoard.getElementAt(point);
        if (element == BoardElement.NONE) {
            return true;
        } else {
            return false;
        }
    }

    public void check_all_act(BoardPoint point) {
        act_up = check_act_up(point);
        act_right = check_act_right(point);
        act_down = check_act_down(point);
        act_left = check_act_left(point);
        if (act_up == true || act_right == true || act_down == true || act_left == true) {
            act = true;
        }
    }

    public boolean check_act_up(BoardPoint point) {
        boolean flag = false;
        BoardPoint p;
        for (int i = 1; i <= radius_act; i++) {
            p = point.shiftTop(i);
            if (gameBoard.getElementAt(p) == BoardElement.WALL) {
                flag = false;
                break;
            } else if (gameBoard.getElementAt(p) == BoardElement.DESTROY_WALL ||
                    gameBoard.getElementAt(p) == BoardElement.MEAT_CHOPPER ||
                    gameBoard.getElementAt(p) == BoardElement.OTHER_BOMBERMAN) {
                flag = true;
                break;
            } else {
                flag = false;
            }
        }
        return flag;
    }

    public boolean check_act_right(BoardPoint point) {
        boolean flag = false;
        BoardPoint p;
        for (int i = 1; i <= radius_act; i++) {
            p = point.shiftRight(i);
            if (gameBoard.getElementAt(p) == BoardElement.WALL) {
                flag = false;
                break;
            } else if (gameBoard.getElementAt(p) == BoardElement.DESTROY_WALL ||
                    gameBoard.getElementAt(p) == BoardElement.MEAT_CHOPPER ||
                    gameBoard.getElementAt(p) == BoardElement.OTHER_BOMBERMAN) {
                flag = true;
                break;
            } else {
                flag = false;
            }
        }
        return flag;
    }

    public boolean check_act_down(BoardPoint point) {
        boolean flag = false;
        BoardPoint p;
        for (int i = 1; i <= radius_act; i++) {
            p = point.shiftBottom(i);
            if (gameBoard.getElementAt(p) == BoardElement.WALL) {
                flag = false;
                break;
            } else if (gameBoard.getElementAt(p) == BoardElement.DESTROY_WALL ||
                    gameBoard.getElementAt(p) == BoardElement.MEAT_CHOPPER ||
                    gameBoard.getElementAt(p) == BoardElement.OTHER_BOMBERMAN) {
                flag = true;
                break;
            } else {
                flag = false;
            }
        }
        return flag;
    }

    public boolean check_act_left(BoardPoint point) {
        boolean flag = false;
        BoardPoint p;
        for (int i = 1; i <= radius_act; i++) {
            p = point.shiftLeft(i);
            if (gameBoard.getElementAt(p) == BoardElement.WALL) {
                flag = false;
                break;
            } else if (gameBoard.getElementAt(p) == BoardElement.DESTROY_WALL ||
                    gameBoard.getElementAt(p) == BoardElement.MEAT_CHOPPER ||
                    gameBoard.getElementAt(p) == BoardElement.OTHER_BOMBERMAN) {
                flag = true;
                break;
            } else {
                flag = false;
            }
        }
        return flag;
    }

//    public TurnAction direction() {
//        int value = 7;
//        int size = gameBoard.size();
//        int half_size = size/2;
//        int x = hero.getX();
//        int y = hero.getY();
//        if (x > (half_size - value) &&
//                x < (half_size + value ) &&
//                y > (half_size - value) &&
//                y < (half_size + value)) {
//            return new TurnAction(act,random());
//        } else {
//            boolean rather_up = if (y > half_size + value);
//            boolean rather_right;
//            boolean rather_down;
//            boolean rather_left;
//        }
//    }
}

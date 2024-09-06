# crossroad-simulation

## Setup
- clone repository
- open project in intellij
- run CrossroadProgram
## Images

![image](https://github.com/user-attachments/assets/601684bd-d167-4682-87ed-90cdcbdca5b7)

Strategies to select in run time.

![image](https://github.com/user-attachments/assets/599e6985-d955-48ba-a86c-a554cc203bff)


Darker green traffic light represents "green arrow for right turn"

Green traffic light represents arrow for any direction.

![image](https://github.com/user-attachments/assets/6802df68-515d-495b-a998-d96288261b8c)


- gray is street
- green is grass, cars can't be there.
- cars are blue,
- green light represents traffic light without collisons.
- dark green light represents traffic light for right turn without collisions.
- we can select traffic strategy in run time. Either FixedIterations - changes activate lane every predefined number of cycles. or busiest lane first, which prioritizes busiest lane, but also takes into consideration the lane that waited too long, has a minimum and maximum limit of active lane.
- cars can turn left right or go forward
- cars when outside of a map, then they are counted to statistic - averageCarsPerIteration which tells how many cars moved through crossroad.

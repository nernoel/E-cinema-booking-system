import { PrismaClient } from "@prisma/client";
import { auth } from "@/app/utils/auth";
import StudySessionCard from "../components/StudySessionCard";
import MyPosts from "../components/MyPosts";
const prisma = new PrismaClient();

// NEED TO WRAP INTO ASYNC FUNCTION WRAPPER BUT WORKS SOMEHOW???
const session = await auth();

// FINDING ALL STUDY SESSIONS
const allPosts = await prisma.post.findMany({
    where: {
        //userEmail: session?.user?.email // email for now but change to name
    }
});

export default function Dashboard() {
    return (
        <div>
        <MyPosts />
        
            <h1>ALL STUDY POSTS</h1>
            <div className="flex">
                {allPosts.map(post => (
                    <StudySessionCard
                        key={post.id} // don't forget to add a unique key
                        title={post.title}
                        description={post.description}
                        userEmail={post.userEmail} 
                        location={""} 
                        isActive={false} 
                        isPrivate={false} 
                        createdAt={""}                    />
                ))}
            </div>
        </div>
    )
}